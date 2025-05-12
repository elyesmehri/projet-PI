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
