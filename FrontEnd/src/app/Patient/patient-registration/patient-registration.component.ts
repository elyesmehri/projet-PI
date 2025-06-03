import { Component } from '@angular/core';
import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import {catchError, delay, EMPTY, of, Subscription, switchMap, tap, throwError} from 'rxjs';
import {CheckPatientRequest, Patient} from '../patient.model';

@Component({

  selector: 'app-patient-registration',
  templateUrl: './patient-registration.component.html',
  styleUrls: ['./patient-registration.component.css']
})

export class PatientRegistrationComponent {

  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  private familyNameSubscription: Subscription | undefined;

  familyId: number | null = null;

  familyName: string = '';

  message: string = '';

  patient_Fill = {

    id : 0,
    patientName: '',
    age: 0,
    gender: false,
    address: '',
    phoneNumber: '',
    medical_state: '',
    password: '',
    confirmPassword: '',
  };

  public passwordStrength: string | undefined; // Explicitly typed
  private familyNameInput: string | undefined;

  constructor(private patientService: PatientService,
              private router: Router,
              private location: Location,
              private familyService: FamilyService) {
  }

  ngOnInit(): void {

    this.familyNameSubscription = this.familyService.currentFamilyName$
      .pipe(
        tap((name: string) => {
          this.familyName = name;
          console.log('Family Name (from BehaviorSubject):', this.familyName);
        }),
        switchMap((name: string) => {
          if (name) {
            console.log(`Attempting to get ID for family: ${name}`);
            return this.familyService.getFamilyIdByName(name); // Returns Observable<number>
          } else {
            console.warn('Family name is empty, skipping ID retrieval.');
            return EMPTY;
          }
        })
      )
      .subscribe({
        next: (id: number) => { // Expecting 'number' here
          this.familyId = id;
          console.log('Family ID retrieved via switchMap:', this.familyId);
        },
        error: (error) => {
          console.error('Error in getting family ID:', error);
          this.familyId = null;
          console.warn('Family ID was not retrieved due to an error (e.g., family not found).');
        }
      });
  }

  checkPasswordStrength(): void {

    const password = this.patient_Fill.password;
    if (password.length < 6) {
      this.passwordStrength = 'Weak';
    } else {
      const hasLower = /[a-z]/.test(password);
      const hasUpper = /[A-Z]/.test(password);
      const hasNumber = /[0-9]/.test(password);
      const hasSpecial = /[^\w\s]/.test(password);

      let strength = 0;
      if (hasLower) strength++;
      if (hasUpper) strength++;
      if (hasNumber) strength++;
      if (hasSpecial) strength++;

      if (strength === 4) {
        this.passwordStrength = 'Strong';
      } else if (strength === 3) {
        this.passwordStrength = 'Medium';
      } else {
        this.passwordStrength = 'Weak';
      }
    }
    console.log('Password Strength:', this.passwordStrength);
  }

  getPasswordStrengthClass(): string {

    switch (this.passwordStrength) {

      case 'Strong':
        return 'text-green-500 font-bold';
      case 'Medium':
        return 'text-yellow-500 font-bold';
      case 'Weak':
        return 'text-red-500 font-bold';
      default:
        return '';
    }
  }


  onSubmit(): void {

    this.message = '';
    this.registrationSuccess = false;
    this.registrationError = false;

    // 3. if so then we can create it in our database with the already working method bellow
    // else just tell that patient is existing ..
    this.patientService.createPatient({

      patientName: this.patient_Fill.patientName,
      age: this.patient_Fill.age,
      gender: this.patient_Fill.gender,
      address: this.patient_Fill.address,
      phoneNumber: this.patient_Fill.phoneNumber,
      password: this.patient_Fill.password,
      medical_state: this.patient_Fill.medical_state,
    }).pipe(
      // --- Part 1: Patient Creation Confirmation ---
      tap((createdPatientResponse: Patient | null) => { // Explicitly type as Patient | null
        if (!createdPatientResponse) {
          // Handle the case where the backend returns 201 but no body, or an empty body
          console.warn('Backend returned 201 Created but with an empty/null response body for patient creation.');
          this.message = `Patient created successfully, but details not returned.`;
          // If you need the ID from the response for subsequent steps, you might
          // need to throw an error here, or rely solely on getPatientIdByName
          // (which is what we're doing as per your request).
        } else {
          // Only access properties if createdPatientResponse is not null
          this.message = `Patient ${createdPatientResponse.patientName || 'unknown'} created successfully!`;
          console.log('Patient created successfully:', createdPatientResponse);
        }
      }),
      // --- Part 2: Add a 3-second delay ---
      delay(3000),

      // --- Part 3: Get Patient ID by Name and then Assign Family ---
      switchMap(() => { // The 'createdPatientResponse' from tap is not directly used here, as per your request to use getPatientIdByName
        const familyId = this.familyId;
        if (familyId == null) {
          const errorMessage = `Failed to assign family: Family ID is missing.`;
          console.error(errorMessage);
          return throwError(() => new Error(errorMessage));
        }

        console.log(`Delay finished. Attempting to get Patient ID for: ${this.patient_Fill.patientName}`);
        return this.patientService.getPatientIdByName(this.patient_Fill.patientName).pipe(
          switchMap((patientId: number) => {
            if (patientId != null) {
              console.log(`Patient ID retrieved by name: ${patientId}. Now assigning family.`);
              return this.patientService.assignFamilyToPatient(patientId, familyId);
            } else {
              const errorMessage = `Failed to retrieve Patient ID by name for '${this.patient_Fill.patientName}'.`;
              console.error(errorMessage);
              return throwError(() => new Error(errorMessage));
            }
          }),
          catchError(err => {
            const errorMessage = `Error retrieving Patient ID by name: ${err.error?.message || err.message || 'Unknown error'}`;
            console.error(errorMessage);
            return throwError(() => new Error(errorMessage));
          })
        );
      }),
      // --- Part 4: General Error Handling for the entire chain ---
      catchError(err => {
        console.error('Error during patient creation or assignment chain:', err);
        this.registrationError = true;
        this.message = err.error?.message || err.message || 'Failed to add and assign patient. Please try again.';
        return throwError(() => err);
      })
    ).subscribe({
      next: (assignedPatient: Patient) => {
        this.registrationSuccess = true;
        this.message = `Patient ${assignedPatient.patientName || 'unknown'} added and assigned to family successfully!`;
        console.log('Final successful assignment result:', assignedPatient);
      },
      error: (err) => {
        this.registrationError = true;
        this.message = err.error?.message || err.message || 'Final error: Failed to add and assign patient.';
        console.error('Final error handler caught:', err);
      }
    });
  }
}

