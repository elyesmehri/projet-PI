import { Component, ViewChild } from '@angular/core';
import { FamilyService } from "../../../services/family.service";
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-family-registration',
  templateUrl: './family-registration.component.html',
  styleUrls: ['./family-registration.component.css']
})

export class FamilyRegistrationComponent {

  @ViewChild('familyForm') familyForm!: NgForm;

  formData = {

    familyname: '',
    password: '',
    confirmedPassword : '',
    relative : '',
    relationship : '',
    address: '',
    phoneNumber: '',
    advice : '',
    invest : 0
  };

  message : string = '';

  passwordStrength: string = '';
  private id: number | undefined;

  constructor(private familyService: FamilyService) {}

  ngOnInit(): void {
    // You can also log here to see the initial state of formData
    console.log('ngOnInit - initial formData:', this.formData);
  }

  onInputChange(): void {
    console.log('formData updated:', this.formData);
    console.log('Family Data ', this.familyForm);

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

  private familyFormIsValid(): boolean {
    return !!this.formData.familyname &&
      !!this.formData.password &&
      !!this.formData.relative;
  }

  onSubmit(): void {

    this.message = '';

    console.log('--- onSubmit initiated ---');

    // Always validate the form and check password match first
    if (this.familyForm.valid && this.formData.password === this.formData.confirmedPassword) {
      console.log('Form is valid and passwords match.');

      // --- THIS IS HOW YOU PUT ALL THE DATA IN THE METHOD CALL ---
      this.familyService.createFamily({

        familyname: this.formData.familyname,
        password: this.formData.password,
        relative: this.formData.relative,
        relationship: this.formData.relationship,
        phoneNumber: this.formData.phoneNumber,
        address: this.formData.address,
        invest: this.formData.invest,
        advice: this.formData.advice,

      }).subscribe({
        next: (response) => {
          this.message = `Family ${response.familyname || 'unknown'} added successfully!`;
          console.log('Family added successfully:', response);
          this.familyForm.resetForm();
          // Reset formData's properties manually after resetting the form
          this.formData = {
            familyname: '', password: '', invest: 0, confirmedPassword: '',
            advice: '', address: '', phoneNumber: '', relative : '', relationship: ''
          };
        },
        error: (err) => {
          console.error('Error adding family:', err);
          this.message = err.error?.message || 'Failed to add family. Please try again.';
        }
      });
    } else {
      this.message = 'Please correct the form errors and ensure passwords match.';
      console.log('Form is invalid or passwords do not match.');
      this.familyForm.control.markAllAsTouched(); // Show validation messages
    }
  }
}
