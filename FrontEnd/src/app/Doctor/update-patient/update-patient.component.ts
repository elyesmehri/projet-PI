import { Component, OnInit, OnDestroy } from '@angular/core';
import { DoctorService } from '../../../services/doctor.service'; // Import DoctorService
import {catchError, EMPTY, filter, finalize, Subscription, switchMap, tap} from 'rxjs';
import {Patient} from '../../Patient/patient.model'
import {Doctor} from "../doctor.model";

@Component({
  selector: 'app-update-patient',
  templateUrl: './update-patient.component.html',
  styleUrls: ['./update-patient.component.css']
})

export class UpdatePatientComponent implements OnInit, OnDestroy {


  doctorname: string = '';
  patients: Patient[] = [];
  doctorData: Doctor | null = null; // New property to hold the full doctor object

  patientsPerPage: number = 10; // Or whatever number of patients you want per page
  currentPage: number = 1;
  isLoadingDoctor: boolean = false; // Loading indicator for doctor data
  doctorMessage: string = ''; // Messages specific to doctor data loading

  private doctorNameSubscription: Subscription | undefined;


  doctorId: number | null = null; // To store the retrieved Doctor ID

  isLoadingId: boolean = false; // Loading indicator for ID retrieval
  idMessage: string = ''; // Message for ID retrieval status

  message : string = '';

  isLoadingDoctorData : boolean = false;

  public doctorPatients: Patient[] = [];

  constructor(private doctorService: DoctorService) { } // Inject DoctorService


  ngOnInit(): void {

    // Reset properties for a fresh start
    this.doctorId = null;
    this.idMessage = '';
    this.isLoadingId = true; // Start loading for ID retrieval

    this.doctorService.currentDoctorName$.pipe(
      // 1. Filter: Only proceed if a non-empty doctorName is emitted
      filter(name => {
        if (!name) {
          console.warn('ngOnInit: Doctor name from BehaviorSubject is empty, cannot retrieve ID/data.');
          this.message = 'No doctor name available to fetch details.';
          this.isLoadingId = false;
          this.isLoadingDoctorData = false;
          return false; // Stop the stream if name is empty
        }
        return true; // Proceed with valid name
      }),
      // 2. Tap: Update the component's doctorName property
      tap((name: string) => {
        this.doctorname = name;
        console.log('ngOnInit: Doctor Name (from BehaviorSubject):', this.doctorname);
        this.message = `Retrieving ID for doctor: '${this.doctorname}'...`;
      }),
      // 3. switchMap (First API Call): Get Doctor ID by Name
      switchMap((name: string) => {
        console.log(`ngOnInit: Calling getDoctorIdByName for: ${name}`);
        this.isLoadingId = true; // Ensure ID loading is active
        return this.doctorService.getDoctorIdByName(name).pipe(
          catchError(err => {
            console.error(`ngOnInit: Error fetching ID for doctor name '${name}':`, err);
            this.message = `Failed to get ID for doctor '${name}'. Patient might not exist or network error.`;
            this.doctorId = null; // Clear ID on error
            this.isLoadingId = false;
            this.isLoadingDoctorData = false;
            return EMPTY; // Stop the stream if ID retrieval fails
          })
        );
      }),
      // 4. Tap: Update the component's doctorId property
      tap((id: number) => {
        this.doctorId = id;
        console.log('ngOnInit: Doctor ID retrieved:', this.doctorId);
        this.message = `Doctor ID ${this.doctorId} retrieved. Fetching full doctor details...`;
        this.isLoadingId = false; // ID retrieval is done
        this.isLoadingDoctorData = true; // Start loading for full data
      }),
      // 5. switchMap (Second API Call): Get Full Doctor Data by ID
      switchMap((id: number) => {
        if (id != null) { // Ensure ID is valid before fetching details
          console.log(`ngOnInit: Attempting to call getDoctorById with ID: ${id}`); // <-- IMPORTANT DEBUG LOG
          return this.doctorService.getDoctorById(id).pipe(
            tap(fullDoctorData => { // <-- tap here to see the data
              this.doctorData = fullDoctorData; // Assign the data
              this.doctorPatients = fullDoctorData.patients || [];

              console.log('ngOnInit: Full Doctor Data retrieved:', fullDoctorData);
              console.log('ngOnInit: Patients assigned from Doctor Data:', this.doctorPatients); // <-- Console log the patients array
            }),
            catchError(err => {
              console.error(`ngOnInit: Error retrieving full doctor details for ID ${id}:`, err);
              this.message = `Failed to retrieve full doctor details for ID ${id}.`;
              this.doctorData = null; // Clear data on error
              this.doctorPatients = [];
              return EMPTY; // Stop the stream if details retrieval fails
            })
          );
        } else {
          console.warn('ngOnInit: Doctor ID is null/undefined after first step, cannot fetch full details.');
          this.message = 'Doctor ID was not found, skipping full details retrieval.';
          this.doctorData = null;
          return EMPTY;
        }
      }),
      // 6. Finalize: Always executed when the entire observable chain completes or errors
      finalize(() => {
        console.log('ngOnInit: Doctor data retrieval chain completed/errored. Setting all loading false.');
        this.isLoadingId = false;
        this.isLoadingDoctorData = false;
      })
    )
      .subscribe({
        // 7. Subscribe 'next': This receives the result of the *last* switchMap (the Doctor object)
        next: (doctor: Doctor) => {
          // this.doctorData is already set in the tap operator before this.
          this.message = `Full details for Doctor '${doctor.doctorname}' retrieved successfully.`;
          console.log('ngOnInit: Final successful retrieval, doctor details set.');
        },
        // 8. Subscribe 'error': This will only be called if an error propagates *past* all catchError operators.
        error: (error) => {
          console.error('ngOnInit: An unhandled error occurred in the doctor data retrieval chain:', error);
          this.message = 'An unexpected error occurred during doctor data retrieval.';
          this.doctorId = null;
          this.doctorData = null;
        }
      });

  }

  ngOnDestroy(): void {
    if (this.doctorNameSubscription) {
      this.doctorNameSubscription.unsubscribe(); // Prevent memory leaks
    }
  }

  get totalPages(): number {
    return Math.ceil(this.doctorPatients.length / this.patientsPerPage);
  }

  // Returns the subset of patients for the current page
  paginatedPatients(): Patient[] {
    const startIndex = (this.currentPage - 1) * this.patientsPerPage;
    return this.doctorPatients.slice(startIndex, startIndex + this.patientsPerPage);
  }

  // Moves to the next page if not on the last page
  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  // Moves to the previous page if not on the first page
  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }
}
