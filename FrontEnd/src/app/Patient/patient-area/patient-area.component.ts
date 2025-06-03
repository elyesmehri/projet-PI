import { Component } from '@angular/core';
import { PatientService } from '../../../services/patient.service'
import {Patient} from "../patient.model";
import {catchError, EMPTY, filter, finalize, Subscription, switchMap, tap} from "rxjs";

@Component({
  selector: 'app-patient-area',
  templateUrl: './patient-area.component.html',
  styleUrls: ['./patient-area.component.css']
})
export class PatientAreaComponent {

  private patientNameSubscription: Subscription | undefined;

  patientName: string = ''; // Will hold the name from BehaviorSubject
  patientGender : String = '';
  patientId: number | null = null; // Will hold the retrieved ID
  patientData: Patient | null = null; // Will hold the full patient object

  isLoading: boolean = false; // Global loading indicator for the entire chain
  message: string = ''; // For displaying status or error messages

  constructor(private patientService: PatientService) { } // Inject DoctorService

  ngOnInit(): void {
    // Initialize properties for a fresh start
    this.patientId = null;
    this.patientData = null;
    this.message = 'Attempting to retrieve patient details...';
    this.isLoading = true; // Start loading

    this.patientService.currentPatientName$
      .pipe(
        // 1. Filter: Only proceed if a non-empty patientName is emitted
        filter(name => {
          if (!name) {
            console.warn('Patient name from BehaviorSubject is empty, cannot retrieve details.');
            this.message = 'No patient name available to fetch details.';
            this.isLoading = false;
            return false; // Stop the stream if name is empty
          }
          return true; // Proceed with valid name
        }),
        // 2. Tap: Update the component's patientName property
        tap((name: string) => {
          this.patientName = name;
          console.log('Patient Name (from BehaviorSubject):', this.patientName);
          this.message = `Retrieving ID for patient: '${this.patientName}'...`;
        }),
        // 3. switchMap (First API Call): Get Patient ID by Name
        switchMap((name: string) => {
          console.log(`Calling getPatientIdByName for: ${name}`);
          return this.patientService.getPatientIdByName(name).pipe(
            catchError(err => {
              console.error(`Error fetching ID for patient name '${name}':`, err);
              this.message = `Failed to get ID for patient '${name}'. Patient might not exist or network error.`;
              this.patientId = null; // Clear ID on error
              return EMPTY; // Stop the stream if ID retrieval fails
            })
          );
        }),
        // 4. Tap: Update the component's patientId property
        tap((id: number) => {
          this.patientId = id;
          console.log('Patient ID retrieved:', this.patientId);
          this.message = `Patient ID ${this.patientId} retrieved. Fetching full patient details...`;
        }),
        // 5. switchMap (Second API Call): Get Full Patient Data by ID
        switchMap((id: number) => {
          if (id != null) { // Ensure ID is valid before fetching details
            console.log(`Calling getPatientById for ID: ${id}`);
            return this.patientService.getPatientById(id).pipe(
              catchError(err => {
                console.error(`Error retrieving full patient details for ID ${id}:`, err);
                this.message = `Failed to retrieve full patient details for ID ${id}.`;
                this.patientData = null; // Clear data on error
                return EMPTY; // Stop the stream if details retrieval fails
              })
            );
          } else {
            console.warn('Patient ID is null/undefined after first step, cannot fetch details.');
            this.message = 'Patient ID was not found, skipping details retrieval.';
            return EMPTY;
          }
        }),
        // 6. Finalize: Always executed when the entire observable chain completes or errors
        finalize(() => {
          this.isLoading = false; // Turn off loading indicator
        })
      )
      .subscribe({
        // 7. Subscribe 'next': This receives the result of the *last* switchMap (the Patient object)
        next: (patient: Patient) => {
          this.patientData = patient; // Store the full patient object
          this.message = `Full details for Patient '${patient.patientName}' retrieved successfully.`;
          console.log('Full Patient Details:', this.patientData);
        },
        // 8. Subscribe 'error': This will only be called if an error propagates *past* all catchError operators.
        //    Given the current structure, most errors are caught and return EMPTY.
        error: (error) => {
          console.error('An unhandled error occurred in the ngOnInit chain:', error);
          this.message = 'An unexpected error occurred during patient data retrieval.';
          this.patientId = null;
          this.patientData = null;
        }
      });
  }

  ngOnDestroy(): void {}

}
