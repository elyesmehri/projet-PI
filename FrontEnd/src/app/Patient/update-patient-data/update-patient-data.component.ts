import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Family } from "../../Family/family.model";
import {BehaviorSubject, catchError, EMPTY, finalize, Observable, of, Subscription, switchMap} from "rxjs";
import { NgForm } from "@angular/forms";
import {Patient} from "../patient.model";


class PatientUpdateRequest {
}

@Component({
  selector: 'app-update-patient-data',
  templateUrl: './update-patient-data.component.html',
  styleUrls: ['./update-patient-data.component.css']
})

export class UpdatePatientDataComponent implements  OnInit, OnDestroy {

  @ViewChild('patientForm') patientForm!: NgForm;

  private familyPatientsSubscription!: Subscription; // Renamed for clarity
  private updateSubscription!: Subscription;
  private familyDetailsSubscription!: Subscription; // For fetching individual patient's family details


  updatePatient = {

    id: 0,
    patientName: '',
    age: 0,
    gender: false,
    address: '',
    phoneNumber: '',
    medical_state: '',
    password: '',
    confirmPassword: '',
    about_me: ''
  };

  foundPatient = {

    id: 0,
    patientName: '',
    age: 0,
    gender: false,
    address: '',
    phoneNumber: '',
    medical_state: '',
    about_me: ''
  };

  errorMessage: string = '';
  message: string = '';

  familyName: string = ''; // Binds to the input field
  isLoading: boolean = false; // To show loading state
  family: Family | undefined;  //  Define the type of the family property

  familyId = 1;

  private currentFamilyNameSubject = new BehaviorSubject<string>('');

  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  private familyNameSubscription: Subscription | undefined;
  protected patients : Patient[] = [];

  currentPatientIndex = 0;
  private submissionSuccess: boolean | undefined;
  private submissionError: boolean | undefined;

  constructor(private patientService: PatientService,
              private familyService: FamilyService,
              private router: Router,
              private location: Location) {
  }

  ngOnInit(): void {
    of(this.familyService.getCurrentFamilyName())
      .pipe(
        switchMap(familyName => {
          if (!familyName) return of(null); // Handle empty name
          return this.familyService.getFamilyIdByName(familyName);
        }),
        switchMap(familyId => {
          if (!familyId) return of(null); // Handle null ID
          return this.familyService.getFamilyById(familyId);
        }),
        catchError(err => {
          console.error('An error occurred in the family fetch flow:', err);
          return of(null);
        })
      )
      .subscribe(family => {
        if (family) {
          this.family = family;
          this.patients = family.patients;
          console.log('Family loaded:', family);
          console.log ('Patients loaded', this.patients);

          if (family.patients.length > 0) {
            const p = family.patients[0]; // or find a specific one by condition

            this.foundPatient = {

              id: p.id,
              patientName: p.patientName,
              age: p.age,
              gender: p.gender,
              address: p.address,
              phoneNumber: p.phoneNumber,
              medical_state: p.medical_state,
              about_me: p.about_me
            };
          }

        } else {
          console.warn('No family data found.');
        }
      });
  }

  getCurrentFamilyName(): string {
    return this.currentFamilyNameSubject.value;
  }

  goHome(): void {
    this.location.back(); // Navigate to the home route
    this.router.navigate (['/familyarea']);

  }

  setPatient(index: number) {

    if (!this.patients || !this.patients[index]) {
      console.warn('Patient data not available at index:', index);

      let currentPatientId = index;

      return;
    }

    const selected = this.patients[index];

    this.foundPatient = {
      id: selected.id,
      patientName: selected.patientName,
      age: selected.age,
      gender: selected.gender,
      address: selected.address,
      phoneNumber: selected.phoneNumber,
      medical_state: selected.medical_state,
      about_me: selected.about_me
    };

    this.updatePatient = {

      id: selected.id,
      patientName: selected.patientName,
      age: selected.age,
      gender: selected.gender,
      address: selected.address,
      phoneNumber: selected.phoneNumber,
      medical_state: selected.medical_state,
      password: '',
      confirmPassword: '',
      about_me: selected.about_me
    };
  }


  nextPatient() {
    if (this.patients && this.patients.length > 0 && this.currentPatientIndex < this.patients.length - 1) {
      this.currentPatientIndex++;
      this.setPatient(this.currentPatientIndex);
    } else {
      console.warn('Cannot go to next patient. Patients list may be empty or undefined.');
    }
  }

  prevPatient() {
    if (this.currentPatientIndex > 0) {
      this.currentPatientIndex--;
      this.setPatient(this.currentPatientIndex);
    }
  }

  onDelete() : void {

    this.patientService.removeFamilyFromPatient(this.foundPatient.id)
      .subscribe(response => {
        // 5. Handle success response from the backend
        this.message = `Patient ${response.patientName || 'unknown'} removed successfully!`;
        this.submissionSuccess = true;
        console.log('Patient removed successfully:', response);
      });
  }

  onSubmit(): void {

    const dataToSend: PatientUpdateRequest = {
      // id: this.updatePatient.id, // EXCLUDED - sent as path variable
      //patientName: this.updatePatient.patientName, // EXCLUDED - per your specific request
      age: this.updatePatient.age,
      gender: this.updatePatient.gender,
      address: this.updatePatient.address,
      phoneNumber: this.updatePatient.phoneNumber,
      medical_state: this.updatePatient.medical_state,
      // password is also not in PatientUpdateRequest, so it's implicitly excluded
      // confirmPassword is explicitly excluded
      about_me: this.updatePatient.about_me
    };

    console.log('Data to be sent for update:', this.updatePatient);

    this.patientService.updatePatient(this.foundPatient.id, dataToSend)

      .subscribe(response => {
        // 5. Handle success response from the backend
        this.message = `Patient ${response.patientName || 'unknown'} updated successfully!`;
        this.submissionSuccess = true;
        console.log('Patient updated successfully:', response);
      });
  }

  ngOnDestroy(): void {}

}


