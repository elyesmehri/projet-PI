import { Component } from '@angular/core';
import {Family} from "../family.model";
import {catchError, distinctUntilChanged, EMPTY, filter, finalize, Subscription, switchMap, tap} from "rxjs";
import {FamilyService} from "../../../services/family.service";
import {AppointmentResponse} from "../../Appointment/appointment.model";
import {AppointmentService} from "../../../services/appointment.service";

@Component({
  selector: 'app-family-appointment',
  templateUrl: './family-appointment.component.html',
  styleUrls: ['./family-appointment.component.css']
})

export class FamilyAppointmentComponent {


  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  familyAppointments: AppointmentResponse[] = []; // To store the fetched appointments
  isLoadingAppointments: boolean = false;       // Loading state for appointments table
  appointmentsMessage: string = '';             // Message for appointments (e.g., "Loading...", "No appointments")

  private familyNameSubscription: Subscription | undefined;

  familyId: number | null = null;

  familyName: string = '';

  message: string = '';

  Our_family: Family | undefined;
  currentFamilyData: Family | null = null;

  Family_Id : number = 0;


  constructor(private familyService: FamilyService,
              private appointmentService : AppointmentService) {}

  ngOnInit(): void {
    console.log('ngOnInit: Starting family ID and appointments retrieval.');
    this.isLoadingAppointments = true; // Set loading to true initially
    this.appointmentsMessage = 'Loading your appointments...'; // Initial message

    this.familyService.currentFamilyName$
      .pipe(
        // 1. Filter: Ensure a valid family name exists
        filter(name => {
          if (!name || name.trim() === '') {
            console.warn('Pipe: Family name is empty, skipping chain.');
            this.familyId = null;
            this.familyAppointments = [];
            this.isLoadingAppointments = false;
            this.appointmentsMessage = 'No family name available to load appointments.';
            return false;
          }
          return true;
        }),
        // 2. distinctUntilChanged: Only emit if the family name has changed
        distinctUntilChanged(),
        tap((name: string) => {
          this.familyName = name;
          console.log('Pipe: Family Name (distinct):', this.familyName);
        }),
        // 3. switchMap: Get Family ID by Name
        switchMap((name: string) => {
          console.log(`Pipe: Attempting to get ID for family: ${name}`);
          return this.familyService.getFamilyIdByName(name).pipe(
            tap(id => {
              if (id !== null) {
                this.familyId = id; // Store the family ID
                console.log('Pipe: Family ID found:', id);
              } else {
                // If ID is null, throw an error to stop the chain gracefully
                throw new Error(`Family ID not found for name: ${name}`);
              }
            }),
            catchError(error => {
              console.error('Pipe: Error getting Family ID:', error);
              this.familyId = null;
              this.familyAppointments = [];
              this.appointmentsMessage = `Family '${this.familyName}' not found or error occurred.`;
              return EMPTY; // Stop the observable stream
            })
          );
        }),
        // --- NEW: 4. switchMap: Get Appointments for the Family ID ---
        switchMap(familyIdFromPreviousPipe => { // This value is the ID emitted by getFamilyIdByName
          // Use this.familyId, which was set in the previous tap
          const idToUse = this.familyId;
          if (idToUse != null) {
            console.log('Pipe: Fetching appointments for Family ID:', idToUse);
            return this.familyService.getFamilyAppointments(idToUse).pipe(
              catchError(error => {
                console.error('Pipe: Error getting appointments for family:', error);
                this.familyAppointments = []; // Clear appointments on error
                this.appointmentsMessage = `Failed to load appointments for '${this.familyName}'.`;
                return EMPTY; // Stop the stream on error
              })
            );
          } else {
            console.warn('Pipe: Family ID is null after retrieval, cannot fetch appointments.');
            this.familyAppointments = [];

            console.log ("Rendez-vous : " + this.familyAppointments);
            this.appointmentsMessage = `Family ID not available to load appointments.`;
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
          this.familyAppointments = appointments;
          console.log('Subscription Next: Successfully retrieved family appointments:', this.familyAppointments);
          if (this.familyAppointments.length === 0) {
            this.appointmentsMessage = 'No appointments found for this family.';
          } else {
            this.appointmentsMessage = ''; // Clear message if data is found
          }
        },
        error: (error) => {
          console.error('Subscription Error: An unhandled error occurred in the appointments chain:', error);
          this.familyAppointments = [];

          this.appointmentsMessage = this.appointmentsMessage || 'An unexpected error occurred during data loading.';
        },
        complete: () => {
          console.log('Subscription Complete: All data fetching for family and appointments is done.');
        }
      });
  }

}
