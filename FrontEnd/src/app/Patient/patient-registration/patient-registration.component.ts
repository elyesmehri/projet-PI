import { Component } from '@angular/core';
import { PatientService } from "../../../services/patient.service";
import { FamilyService } from "../../../services/family.service";
import { Router } from "@angular/router";
import { Location } from "@angular/common";

@Component({
  selector: 'app-patient-registration',
  templateUrl: './patient-registration.component.html',
  styleUrls: ['./patient-registration.component.css']
})

export class PatientRegistrationComponent {

  registrationSuccess: boolean = false;
  registrationError: boolean = false;

  familyname : string | undefined;

  patient_Fill = {
    doctorname : '',
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
  }

  patient_data = {
    patientName : this.patient_Fill.patientName,
    doctorname: this.patient_Fill.doctorname,
    age : this.patient_Fill.age,
    gender : this.patient_Fill.gender,
    address : this.patient_Fill.address,
    phoneNumber : this.patient_Fill.phoneNumber,
    password : this.patient_Fill.password,
    medical_state : this.patient_Fill.medical_state,
  };

  private patientFormIsValid(): boolean {

    return !!this.patient_data.patientName &&
      !!this.patient_data.password &&
      !!this.patient_data.age;
  }

  onSubmit (patient_data = {
    id: 0,
    patientName: this.patient_Fill.patientName,
    doctorname: this.patient_Fill.doctorname,
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

          this.router.navigate(['/family']);

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


