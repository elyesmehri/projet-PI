import { Component } from '@angular/core';
import {PatientService} from "../../../services/patient.service";
import {Patient} from "../patient.model";
import {DoctorService} from "../../../services/doctor.service";


@Component({
  selector: 'app-patient-registration',
  templateUrl: './patient-registration.component.html',
  styleUrls: ['./patient-registration.component.css']
})



export class PatientRegistrationComponent {

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
  }

}
