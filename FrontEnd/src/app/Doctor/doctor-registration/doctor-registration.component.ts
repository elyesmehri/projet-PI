import { Component } from '@angular/core';
import {DoctorService} from "../../../services/doctor.service";
import {Doctor} from "../doctor.model";

@Component({
  selector: 'app-doctor-registration',
  templateUrl: './doctor-registration.component.html',
  styleUrls: ['./doctor-registration.component.css']
})
export class DoctorRegistrationComponent {


  doctor = {
    doctorname: '',
    gender : '',
    speciality: '',
    address: '',
    score: 0,
    phonenumber: '',
    insurance: '',
    hospital: '',
    numberofpatients: 0,
    password: '',
    confirmPassword : ''

  };

  constructor(private doctorservice: DoctorService) {}

  onSubmit() {
    if (this.doctorFormIsValid()) {
      console.log("Doctor submitted: ", this.doctor);

      this.doctorservice.addOne(this.doctor).subscribe({
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
    return !!this.doctor.doctorname &&
      !!this.doctor.password &&
      !!this.doctor.speciality;
  }

}
