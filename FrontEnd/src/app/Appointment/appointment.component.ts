import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { DoctorService } from '../../services/doctor.service';
import { Doctor } from '../Doctor/doctor.model';
import {AppointmentService} from "../../services/appointment.service";
import { FamilyService } from "../../services/family.service";
import {BehaviorSubject, catchError, EMPTY, finalize, forkJoin, Subscription, switchMap, throwError} from "rxjs";
import {AppointmentRequest, AppointmentResponse} from "./appointment.model";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit {

  appointmentFill = {

    familyname: '',
    doctorname: '',
    date: new Date(),
    emergency: 0,
    description: '',
    speciality: '',
  }

  appointmentTobeSend = {

    familyId : 0,
    doctorId : 0,
    date : this.appointmentFill.date,
    emergency : this.appointmentFill.emergency,
    description : this.appointmentFill.description,
  }

  emergencyLevels = [
    {label: '🚨 Urgent', value: 1},
    {label: 'High', value: 2},
    {label: 'Medium', value: 3},
    {label: 'Low', value: 4},
  ];

  emergencyValue: number = 0;
  emergencyText: string = 'Good';
  emergencyClass: string = 'good'; // Initial class


  message: string = '';
  isLoading: boolean = false;
  appointmentSuccess: boolean = false;


  private currentFamilyNameSubject = new BehaviorSubject<string>(''); // BehaviorSubject
  public currentFamilyName$ = this.currentFamilyNameSubject.asObservable(); // Observable

  submissionError: string = '';

  doctorId!: number;
  doctor!: Doctor; // store doctor information


  constructor(
    private route: ActivatedRoute,
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private familyService : FamilyService,
    private router : Router
  ) {
  }

  ngOnInit() {
    const appointmentId = this.route.snapshot.paramMap.get('id');

    this.doctorId = +this.route.snapshot.paramMap.get('id')!;
    console.log('Doctor ID from URL:', this.doctorId);
    this.getDoctorInfo();
  }

  updateEmergencyText(): void {

    if (this.emergencyValue >= 0 && this.emergencyValue <= 20) {
      this.emergencyText = 'Good';
      this.emergencyClass = 'good';
    } else if (this.emergencyValue > 20 && this.emergencyValue <= 40) {
      this.emergencyText = 'Medium';
      this.emergencyClass = 'medium';
    } else if (this.emergencyValue > 40 && this.emergencyValue <= 60) {
      this.emergencyText = 'moderate';
      this.emergencyClass = 'moderate';
    } else if (this.emergencyValue > 60 && this.emergencyValue <= 80) {
      this.emergencyText = 'Bad';
      this.emergencyClass = 'bad';
    } else if (this.emergencyValue > 80 && this.emergencyValue <= 100) {
      this.emergencyText = 'Urgent';
      this.emergencyClass = 'urgent';
    }
  }

  getDoctorInfo() {
    this.doctorService.getDoctorById(this.doctorId).subscribe(
      (doctor) => {
        this.doctor = doctor; // Store doctor data
        console.log('Doctor data:', this.doctor);

        // After doctor data is fetched, update appointmentData with doctor information
        this.appointmentFill.doctorname = this.doctor.doctorname;
        this.appointmentFill.speciality = this.doctor.speciality;  // If needed
      },
      (error) => {
        console.error('Error fetching doctor:', error);
      }
    );
  }


  submitAppointment (): void {

    this.message = '';
    this.isLoading = true;
    this.appointmentSuccess = false;

    // --- Basic Client-Side Validation ---
    if (!this.appointmentFill.familyname || !this.appointmentFill.doctorname || !this.appointmentFill.date || !this.appointmentFill.description) {
      this.message = 'Please fill in all required appointment details (Family Name, Doctor Name, Date, Description).';
      this.isLoading = false;
      return;
    }

  // --- Step 1 & 2: Resolve Family ID and Doctor ID concurrently ---
    forkJoin({
      familyId: this.familyService.getFamilyIdByName(this.appointmentFill.familyname).pipe(
        catchError(err => {
          console.error(`Error getting Family ID for '${this.appointmentFill.familyname}':`, err);
          return throwError(() => new Error(`Family '${this.appointmentFill.familyname}' not found.`));
        })
      ),
      doctorId: this.doctorService.getDoctorIdByName(this.appointmentFill.doctorname).pipe(
        catchError(err => {
          console.error(`Error getting Doctor ID for '${this.appointmentFill.doctorname}':`, err);
          return throwError(() => new Error(`Doctor '${this.appointmentFill.doctorname}' not found.`));
        })
      )
    }).pipe(
      // Step 3: Use resolved IDs to create the AppointmentRequest
      switchMap(results => {
        const familyId = results.familyId;
        const doctorId = results.doctorId;

        if (familyId == null || doctorId == null) { // Should be caught by catchError above, but good check
          return throwError(() => new Error('Could not resolve both Family ID and Doctor ID.'));
        }

        console.log(`Resolved IDs - Family ID: ${familyId}, Doctor ID: ${doctorId}`);

        const appointmentBody  = {

          familyId: familyId,
          doctorId: doctorId,
          date: this.appointmentFill.date,
          description: this.appointmentFill.description,
          emergency: this.appointmentFill.emergency, // Ensure this matches backend type
        }

        console.log('Appointment Request ready:', appointmentBody);


        // Step 4: Call createAppointment with the constructed request
        return this.appointmentService.createAppointment(appointmentBody).pipe(
          catchError(err => {
            console.error('Error creating appointment:', err);
            return throwError(() => new Error(`Failed to create appointment: ${err.error?.message || err.message || 'Unknown error'}`));
          })
        );
      }),
      // Handle any errors from the entire chain
      catchError(err => {
        console.error('Full appointment creation chain error:', err);
        this.message = err.message;
        return EMPTY; // Stop the observable stream on error
      }),
      // Always stop loading when the chain completes or errors
      finalize(() => {
        this.isLoading = false;
      })
    )
      .subscribe({
        next: (response: AppointmentResponse) => {
          this.message = `Appointment created successfully! ID: ${response.id}`;
          this.appointmentSuccess = true;
          console.log('Appointment creation response:', response);
          // Optionally, reset the form here
          // this.appointmentFill = { familyname: '', doctorname: '', date: new Date(), ... };
        },
        error: (err) => {
          // This block will only be hit if an error was not handled by earlier catchError and propagated.
          console.error('Final subscribe error for appointment creation:', err);
          this.message = this.message || 'An unexpected error occurred during appointment creation.';
        }
      });


  }

}
