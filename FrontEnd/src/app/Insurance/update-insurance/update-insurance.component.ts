import { Component } from '@angular/core';

@Component({
  selector: 'app-update-insurance',
  templateUrl: './update-insurance.component.html',
  styleUrls: ['./update-insurance.component.css']
})
export class UpdateInsuranceComponent {

  formData = {
    investmentAmount: null,
    insuranceTitle: '',
    description: '',
    expirationDate: null,
    medicalCoverage: false
  };

  constructor() { }

  onSubmit(formValue: any) {
    console.log('Form Submitted!', formValue);
    // Here you would typically send the formValue to a service or API
    alert('Form submitted successfully! Check console for data.');
  }
}
