import { Component } from '@angular/core';
<<<<<<< HEAD
import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";
import {Observable} from "rxjs";
=======
import {PatientService} from "../../../services/patient.service";
import {Patient} from "../patient.model";
import {DoctorService} from "../../../services/doctor.service";
>>>>>>> 2254f2f9aaa23548609a463cd46d74596f3847d0


@Component({
  selector: 'app-patient-registration',
  templateUrl: './patient-registration.component.html',
  styleUrls: ['./patient-registration.component.css']
})



export class PatientRegistrationComponent {

<<<<<<< HEAD
  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  familyname : string | undefined;

  patient_Fill = {
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

  constructor(private patientService: PatientService,
              private router : Router,
              private location : Location,
              private familyService : FamilyService) {
  }

  ngOnInit(): void {
    this.retrieveFamilyName();
  }

  retrieveFamilyName(): void {
    this.familyService.getFamilyName().subscribe(
      (familyname) => {
        this.familyname = familyname;
        console.log('Family Name retrieved:', this.familyname);
        // You can now use this.familyName in your component's logic or template
      },
      (error) => {
        console.error('Error retrieving family name:', error);
        this.familyname = 'Error fetching name'; // Handle the error in the UI
      }
    );
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
=======
  patient_Fill = {
    patientName: '',
    age : 0,
    gender : false,
    address: '',
    phoneNumber: 0,
    medical_state : '',
    password: '',
    confirmPassword : '',
  };

  patient_data = {
    patientName : this.patient_Fill.patientName,
    age : this.patient_Fill.age,
    gender : this.patient_Fill.gender,
    address : this.patient_Fill.address,
    phoneNumber : this.patient_Fill.phoneNumber,
    password : this.patient_Fill.password,
    medical_state : this.patient_Fill.medical_state,
  };

  constructor(private patientService: PatientService) {}

  onSubmit() {

    console.log ("onSubmit () here! ");
    if (this.patientFormIsValid()) {

      console.log ("Validatiion here !");
      console.log("Patient submitted: ", this.patient_data);

      this.patientService.addOne(this.patient_data).subscribe({

        next: (newPatient) => {
          console.log('Patient registered:', newPatient);
          // Redirect, show success, etc.

        },
        error: (err) => {
          console.error('Registration failed:', err);
        }
      });
    }
    else {
      console.log ("Field Wrong !");
      console.log("Patient body: ", this.patient_data);

    }

  }

  private patientFormIsValid(): boolean {

    return !!this.patient_data.patientName &&
      !!this.patient_data.password &&
      !!this.patient_data.age;
>>>>>>> 2254f2f9aaa23548609a463cd46d74596f3847d0
  }

  onSubmit(patient_data = {
    id: 0,
    patientName: this.patient_Fill.patientName,
    age: this.patient_Fill.age,
    gender: this.patient_Fill.gender,
    address: this.patient_Fill.address,
    phoneNumber: this.patient_Fill.phoneNumber,
    password: this.patient_Fill.password,
    medical_state: this.patient_Fill.medical_state,
  }) {

    console.log("Patient submitted: ", patient_data);

    this.patientService.addOne(patient_data).subscribe({

      next: (newPatient) => {

        console.log('Patient registered:', newPatient);

        this.registrationSuccess = true;
        this.registrationError = false;


        setTimeout(() => {

//          this.location.back ();

        }, 3000);

      },
      error: (err) => {

        console.error('Registration failed:', err);

        this.registrationSuccess = false;
        this.registrationError = true;
      }
    });
  }
}


