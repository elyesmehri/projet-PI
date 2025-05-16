import { Component } from '@angular/core';
import { PatientService } from '../../../services/patient.service'
import {Patient} from "../patient.model";
import {Subscription} from "rxjs";
import {DoctorService} from "../../../services/doctor.service";

@Component({
  selector: 'app-patient-area',
  templateUrl: './patient-area.component.html',
  styleUrls: ['./patient-area.component.css']
})
export class PatientAreaComponent {


  patientName: string = '';
  patientGender : String = '';

  patient = {
    doctorname: '',
    speciality: '',
    address: '',
    score: 0,
    phonenumber: '',
    insurance: '',
    hospital: '',
    numberofpatients: 0,
    password: ''
  };

  private patientNameSubscription: Subscription | undefined;

  constructor(private patientService: PatientService) { } // Inject DoctorService


  ngOnInit(): void {
    this.patientNameSubscription = this.patientService.currentPatientName$.subscribe(
      (name) => {
        this.patientName = name;
        console.log('Patient Name:', this.patientName);

      }
    );
  }
}
