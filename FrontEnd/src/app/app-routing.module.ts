import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';


import { CreditComponent } from './pages/credit/credit.component';
import { StatusComponent } from './pages/status/status.component';
import { ArchiveComponent } from './pages/archive/archive.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { DocumentationComponent } from './pages/documentation/documentation.component';

// Doctor
import { EspaceDoctorComponent } from './Doctor/espace-doctor/espace-doctor.component';
import { DoctorRegistrationComponent } from './Doctor/doctor-registration/doctor-registration.component';
import { UpdatedoctorpasswordComponent } from './Doctor/updatedoctorpassword/updatedoctorpassword.component';
import { DoctorAreaComponent } from './Doctor/doctor-area/doctor-area.component';
import { PersonalDataDoctorComponent } from './Doctor/personal-data-doctor/personal-data-doctor.component';
import { DoctorApppointmentComponent } from './Doctor/doctor-apppointment/doctor-apppointment.component';
import { BrowsingHistoryComponent } from './Doctor/browsing-history/browsing-history.component';

// Patient
import { EspacePatientComponent } from './Patient/espace-patient/espace-patient.component';
import { PatientAreaComponent } from './Patient/patient-area/patient-area.component';
import { PatientRegistrationComponent } from './Patient/patient-registration/patient-registration.component';
import { UpdatepatientpasswordComponent } from './Patient/updatepatientpassword/updatepatientpassword.component';
import { UpdatePatientDataComponent } from './Patient/update-patient-data/update-patient-data.component';
import { UpdatePatientComponent } from './Doctor/update-patient/update-patient.component';

// Family
import { EspaceFamilleComponent } from './Family/espace-famille/espace-famille.component';
import { FamilyRegistrationComponent } from './Family/family-registration/family-registration.component';
import { MedicalAssitantQComponent } from './Family/medical-assitant-q/medical-assitant-q.component';
import { FamilyAreaComponent } from './Family/family-area/family-area.component';
import { PersonalDataFamilyComponent } from './Family/personal-data-family/personal-data-family.component';
import { FamilyAppointmentComponent } from './Family/family-appointment/family-appointment.component';
import { UpdatefamilypasswordComponent } from './Family/updatefamilypassword/updatefamilypassword.component';

// Appointment
import { AppointmentComponent } from './Appointment/appointment.component';

// Insurance
import { UpdateInsuranceFamilyComponent } from './Family/update-insurance-family/update-insurance-family.component';
import { UpdateInsuranceDoctorComponent } from './Doctor/update-insurance-doctor/update-insurance-doctor.component';

const routes: Routes = [

  // Default
  { path: '', component: HomeComponent },

  { path: 'admin/status', component: StatusComponent },
  { path: 'menu_insurances', component: ArchiveComponent },
  { path: 'admin/credits', component: CreditComponent },
  { path: 'admin/documentation', component: DocumentationComponent },
  { path: 'menu_doctors', component: SettingsComponent },

  // Appointment
  { path: 'rdv/:id', component: AppointmentComponent },

  // Doctor
  { path: 'doctor', component: EspaceDoctorComponent },
  { path: 'doctorarea', component: DoctorAreaComponent },
  { path: 'registerdoctor', component: DoctorRegistrationComponent },
  { path: 'passworddoctor', component: UpdatedoctorpasswordComponent },
  { path: 'personaldatadoctor', component: PersonalDataDoctorComponent },
  { path: 'doctorrdv', component: DoctorApppointmentComponent },
  { path: 'update_doctors_patient', component: UpdatePatientComponent },
  { path: 'historydoctor', component: BrowsingHistoryComponent },

  // Patient
  { path: 'patient', component: EspacePatientComponent },
  { path: 'registerpatient', component: PatientRegistrationComponent },
  { path: 'passwordpatient', component: UpdatepatientpasswordComponent },
  { path: 'patientarea', component: PatientAreaComponent },
  { path: 'updatepatient', component: UpdatePatientDataComponent },

  // Family
  { path: 'family', component: EspaceFamilleComponent },
  { path: 'familyarea', component: FamilyAreaComponent },
  { path: 'regiterfamily', component: FamilyRegistrationComponent },
  { path: 'passwordfamily', component: UpdatefamilypasswordComponent },
  { path: 'personaldatafamily', component: PersonalDataFamilyComponent },
  { path: 'medicalassitantq', component: MedicalAssitantQComponent },
  { path: 'familyrdv', component: FamilyAppointmentComponent },
  { path: 'update-insurance-family/:id', component: UpdateInsuranceFamilyComponent },
  { path: 'update-insurance-doctor/:id', component: UpdateInsuranceDoctorComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
