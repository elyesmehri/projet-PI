import {Component, ViewChild} from '@angular/core';
import {DoctorService} from "../../../services/doctor.service";
import {Doctor} from "../doctor.model";
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-doctor-registration',
  templateUrl: './doctor-registration.component.html',
  styleUrls: ['./doctor-registration.component.css']
})


export class DoctorRegistrationComponent {

  @ViewChild('doctorForm') doctorForm!: NgForm;


  formData = {

    doctorname: '',
    medicalID: '',
    password: '' ,
    confirmPassword : '',
    speciality: '',
    address: '',
    phonenumber: '',
    hospital: '',
    gender: false
  };

  message : string = '';

  passwordStrength: string = '';
  private id: number | undefined;

  constructor(private doctorService: DoctorService) {}

  ngOnInit(): void {
    // You can also log here to see the initial state of formData
    console.log('ngOnInit - initial formData:', this.formData);
  }

  onInputChange(): void {
    console.log('formData updated:', this.formData);
    console.log('Doctor Data ', this.doctorForm);

  }

  checkPasswordStrength(): void {
    const password = this.formData.password;
    if (password.length < 6) {
      this.passwordStrength = 'Weak';
    } else {
      const hasLower = /[a-z]/.test(password);
      const hasUpper = /[A-Z]/.test(password);
      const hasNumber = /[0-9]/.test(password);
      const hasSpecial = /[^\w\s]/.test(password); // Matches any non-word character (excluding whitespace)

      let strength = 0;
      if (hasLower) strength++;
      if (hasUpper) strength++;
      if (hasNumber) strength++;
      if (hasSpecial) strength++;

      if (strength === 4) {
        this.passwordStrength = 'Strong';
      } else if (strength >= 2) {
        this.passwordStrength = 'Medium';
      } else {
        this.passwordStrength = 'Weak';
      }
    }
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

  private doctorFormIsValid(): boolean {
    return !!this.formData.doctorname &&
      !!this.formData.password &&
      !!this.formData.speciality;
  }

  onSubmit(): void {

    this.message = '';
    console.log('--- onSubmit initiated ---');

    // Always validate the form and check password match first
    if (this.doctorForm.valid && this.formData.password === this.formData.confirmPassword) {
      console.log('Form is valid and passwords match.');

      // --- THIS IS HOW YOU PUT ALL THE DATA IN THE METHOD CALL ---
      this.doctorService.createDoctor({

        doctorname: this.formData.doctorname,
        medicalID: this.formData.medicalID,
        password: this.formData.password,
        speciality: this.formData.speciality,
        address: this.formData.address,
        phonenumber: this.formData.phonenumber,
        hospital: this.formData.hospital,
        gender: this.formData.gender

      }).subscribe({
        next: (response) => {
          this.message = `Doctor ${response.doctorname || 'unknown'} added successfully!`;
          console.log('Doctor added successfully:', response);
          this.doctorForm.resetForm();
          // Reset formData's properties manually after resetting the form
          this.formData = {
            doctorname: '', medicalID: '', password: '', confirmPassword: '',
            speciality: '', address: '', phonenumber: '', hospital: '', gender: false
          };
        },
        error: (err) => {
          console.error('Error adding doctor:', err);
          this.message = err.error?.message || 'Failed to add doctor. Please try again.';
        }
      });
    } else {
      this.message = 'Please correct the form errors and ensure passwords match.';
      console.log('Form is invalid or passwords do not match.');
      this.doctorForm.control.markAllAsTouched(); // Show validation messages
    }
  }
}
