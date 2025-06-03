import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { DoctorService } from '../../services/doctor.service';
import { Doctor } from '../Doctor/doctor.model';
import {AppointmentService} from "../../services/appointment.service";
import {Appointment} from "./appointment.model";
import {Location} from "@angular/common";
import { FamilyService } from "../../services/family.service";
import {BehaviorSubject, Subscription} from "rxjs";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit {

  // Data for appointment request (fields you need to submit)
  appointmentData: Appointment = {
    id: 0, // optional or ignored
    familyname: '',
    doctorname: '',
    date: new Date(),
    nature: 0,
    period: 0,
    tariff: 0,
    emergency: 0,
    quoted: false,
    description: '',
    skipped: false,
  };

  appointmentFill = {

    familyname: '',
    doctorname: '',
    date: new Date(),
    emergency: 0,
    description: '',
    speciality: '',
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

  submitAppointment() {

    const appointment: Appointment = {
      id: 0,
      familyname: this.appointmentFill.familyname,
      doctorname: this.appointmentFill.doctorname,
      date: this.appointmentFill.date,
      nature: 0,
      emergency: this.emergencyValue,
      period: 0,     // you can change this based on logic
      tariff: 0,   // or fetch it dynamically
      quoted: false, // or true based on checkbox or rule
      description: this.appointmentFill.description,
      skipped: false
    };

    this.appointmentService.BookAppointment(appointment).subscribe({
      next: (res) => {
      },
      error: (err) => {

        const familyname = this.appointmentFill.familyname;
        this.familyService.setFamilyName(familyname);

        console.log('✔️ Appointment booked successfully. Emitted family name:', familyname);
        this.router.navigate (['familyarea'])
        // You can add other actions here if needed, or leave it as is.
      }
    });
  }
}
