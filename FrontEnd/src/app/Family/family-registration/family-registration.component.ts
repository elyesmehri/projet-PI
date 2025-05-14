import { Component } from '@angular/core';
import { FamilyService } from '../../../services/family.service';
import { Family } from "../family.model";
import {DoctorService} from "../../../services/doctor.service";


@Component({
  selector: 'app-family-registration',
  templateUrl: './family-registration.component.html',
  styleUrls: ['./family-registration.component.css']
})


export class FamilyRegistrationComponent {

  passwordStrength: string = '';

  family = {

    id: '',
    familyname: '',
    patientname: '',
    relative: '',
    relationship : '',
    address: '',
    phonenumber: '',
    insurance: '',
    password: '',
    invest: '',
    confirmPassword : ''
  };

  investControl: any;

  constructor(private familyservice: FamilyService) {
  }

  checkPasswordStrength(): void {
    const password = this.family.password;
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


    const partialFamily = {
      familyname: this.family.familyname,
      patientname: this.family.patientname,
      relative: this.family.relative,
      relationship: this.family.relationship,
      address: this.family.address,
      phonenumber: this.family.phonenumber,
      insurance: this.family.insurance,
      password: this.family.password,
      invest: this.family.invest
    };

    this.familyservice.addNewFamily(partialFamily).subscribe({

      next: (response: any) => {
        console.log('✅ Family added successfully', response);
        // Redirect or show success message
      },
      error: (err: any) => {
        console.error('❌ Failed to add family', err);
        // Show error message to the user
      }
    });
  }
}
