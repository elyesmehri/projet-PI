import { Component } from '@angular/core';
import {DoctorService} from "../../../services/doctor.service";
import {Doctor} from "../doctor.model";

@Component({
  selector: 'app-doctor-registration',
  templateUrl: './doctor-registration.component.html',
  styleUrls: ['./doctor-registration.component.css']
})


export class DoctorRegistrationComponent {

  doctorFill = {

    doctorname: '',
    gender: true,
    speciality: '',
    address: '',
    phonenumber: 0,
    insurance: '',
    hospital: '',
    numberofpatients: 0,
    password: '',
    confirmPassword: ''
  };

  doctor_data = {

    doctorname: '',
    gender: true,
    speciality: '',
    address: '',
    phonenumber: 0,
    insurance: '',
    hospital: '',
    password: '',
  };

  doctorForm: any; // You might want to type this as NgForm if you import it
  passwordStrength: string = '';

  constructor(private doctorservice: DoctorService) {}

  checkPasswordStrength(): void {
    const password = this.doctorFill.password;
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

  onSubmit() {
    if (this.doctorFormIsValid()) {
      console.log("Doctor submitted: ", this.doctor_data);

      this.doctorservice.addOne(this.doctor_data).subscribe({
        next: (newDoctor) => {
          console.log('Doctor registered:', newDoctor);
          // Redirect, show success, etc.
        },
        error: (err) => {
          console.error('Registration failed:', err);
        }
      });
    }
  }

  private doctorFormIsValid(): boolean {
    return !!this.doctor_data.doctorname &&
      !!this.doctor_data.password &&
      !!this.doctor_data.speciality;
  }

}
