import { Component } from '@angular/core';
import { Doctor } from "../doctor.model";
import { DoctorService} from "../../../services/doctor.service";

import {catchError, distinctUntilChanged, EMPTY, filter, finalize, Subscription, switchMap, tap} from "rxjs";

import { AppointmentResponse } from "../../Appointment/appointment.model";
import { AppointmentService } from "../../../services/appointment.service";


@Component({
  selector: 'app-doctor-apppointment',
  templateUrl: './doctor-apppointment.component.html',
  styleUrls: ['./doctor-apppointment.component.css']
})


export class DoctorApppointmentComponent {


  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  doctorAppointments: AppointmentResponse[] = []; // To store the fetched appointments
  isLoadingAppointments: boolean = false;       // Loading state for appointments table
  appointmentsMessage: string = '';             // Message for appointments (e.g., "Loading...", "No appointments")

  private doctorNameSubscription: Subscription | undefined;

  doctorname : string = '';

  message: string = '';

  DoctorData : Doctor | undefined;
  currentDoctorData: Doctor | null = null;

  doctorId : number = 0;


  constructor(private doctorService : DoctorService,
              private appointmentService : AppointmentService) {}


  ngOnInit(): void {

    console.log('ngOnInit: Starting doctor ID and appointments retrieval.');
    this.isLoadingAppointments = true; // Set loading to true initially
    this.appointmentsMessage = 'Loading your appointments...'; // Initial message

    this.doctorService.currentDoctorName$
      .pipe(
        // 1. Filter: Ensure a valid doctor name exists
        filter(name => {
          if (!name || name.trim() === '') {
            console.warn('Pipe: Doctor name is empty, skipping chain.');
            this.doctorId = 0;
            this.doctorAppointments = [];
            this.isLoadingAppointments = false;
            this.appointmentsMessage = 'No doctor name available to load appointments.';
            return false;
          }
          return true;
        }),
        // 2. distinctUntilChanged: Only emit if the doctor name has changed
        distinctUntilChanged(),
        tap((name: string) => {
          this.doctorname = name;
          console.log('Pipe: Doctor Name (distinct):', this.doctorname);
        }),
        // 3. switchMap: Get Doctor ID by Name
        switchMap((name: string) => {
          console.log(`Pipe: Attempting to get ID for doctor: ${name}`);
          return this.doctorService.getDoctorIdByName(name).pipe(
            tap(id => {
              if (id !== null) {
                this.doctorId = id; // Store the doctor ID
                console.log('Pipe: Doctor ID found:', id);
              } else {
                // If ID is null, throw an error to stop the chain gracefully
                throw new Error(`Doctor ID not found for name: ${name}`);
              }
            }),
            catchError(error => {
              console.error('Pipe: Error getting Doctor ID:', error);
              this.doctorId = 0;
              this.doctorAppointments = [];
              this.appointmentsMessage = `Doctor '${this.doctorname}' not found or error occurred.`;
              return EMPTY; // Stop the observable stream
            })
          );
        }),
        // --- NEW: 4. switchMap: Get Appointments for the Doctor ID ---
        switchMap(doctorIdFromPreviousPipe => { // This value is the ID emitted by getDoctorIdByName
          // Use this.doctorId, which was set in the previous tap
          const idToUse = this.doctorId;
          if (idToUse != null) {
            console.log('Pipe: Fetching appointments for Doctor ID:', idToUse);
            return this.doctorService.getDoctorAppointments(idToUse).pipe(
              catchError(error => {
                console.error('Pipe: Error getting appointments for doctor:', error);
                this.doctorAppointments = []; // Clear appointments on error
                this.appointmentsMessage = `Failed to load appointments for '${this.doctorname}'.`;
                return EMPTY; // Stop the stream on error
              })
            );
          } else {
            console.warn('Pipe: Doctor ID is null after retrieval, cannot fetch appointments.');
            this.doctorAppointments = [];

            console.log ("Rendez-vous : " + this.doctorAppointments);
            this.appointmentsMessage = `Doctor ID not available to load appointments.`;
            return EMPTY;
          }
        }),
        // finalize: Always runs when the entire chain completes or errors
        finalize(() => {
          this.isLoadingAppointments = false; // Turn off loading indicator
          console.log('ngOnInit pipe chain completed/errored.');
        })
      )
      .subscribe({
        next: (appointments: AppointmentResponse[]) => { // This `next` now receives the final appointments array
          this.doctorAppointments = appointments;
          console.log('Subscription Next: Successfully retrieved doctor appointments:', this.doctorAppointments);
          if (this.doctorAppointments.length === 0) {
            this.appointmentsMessage = 'No appointments found for this doctor.';
          } else {
            this.appointmentsMessage = ''; // Clear message if data is found
          }
        },
        error: (error) => {
          console.error('Subscription Error: An unhandled error occurred in the appointments chain:', error);
          this.doctorAppointments = [];

          this.appointmentsMessage = this.appointmentsMessage || 'An unexpected error occurred during data loading.';
        },
        complete: () => {
          console.log('Subscription Complete: All data fetching for doctor and appointments is done.');
        }
      });
  }
}
