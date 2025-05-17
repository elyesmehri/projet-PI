import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

import { CreditComponent } from './pages/credit/credit.component';
import { StatusComponent } from './pages/status/status.component';
import { ArchiveComponent } from './pages/archive/archive.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { DocumentationComponent } from './pages/documentation/documentation.component';

import { EspaceDoctorComponent } from './Doctor/espace-doctor/espace-doctor.component';
import { DoctorRegistrationComponent } from './Doctor/doctor-registration/doctor-registration.component';
import { UpdatedoctorpasswordComponent } from './Doctor/updatedoctorpassword/updatedoctorpassword.component';
import { DoctorAreaComponent } from './Doctor/doctor-area/doctor-area.component';


import { EspacePatientComponent } from './Patient/espace-patient/espace-patient.component';
import { PatientAreaComponent } from './Patient/patient-area/patient-area.component';
import { PatientRegistrationComponent } from './Patient/patient-registration/patient-registration.component';
import { UpdatepatientpasswordComponent } from './Patient/updatepatientpassword/updatepatientpassword.component';
import { UpdatePatientDataComponent } from './Patient/update-patient-data/update-patient-data.component';

import { EspaceFamilleComponent } from './Family/espace-famille/espace-famille.component';
import { FamilyRegistrationComponent } from './Family/family-registration/family-registration.component';
import { MedicalAssitantQComponent } from './Family/medical-assitant-q/medical-assitant-q.component';
import { FamilyAreaComponent } from './Family/family-area/family-area.component';

import { AppointmentComponent } from './appointment/appointment.component';

import { PersonalDataFamilyComponent } from './Family/personal-data-family/personal-data-family.component';

const routes: Routes = [

  { path: '', component: HomeComponent },
  { path: 'admin/status', component: StatusComponent },
  { path: 'menu_insurances', component: ArchiveComponent },
  { path: 'admin/credits', component: CreditComponent },

  { path: 'admin/documentation', component: DocumentationComponent },
  { path: 'menu_doctors', component: SettingsComponent },

  { path: 'doctor', component: EspaceDoctorComponent },
  { path: 'registerdoctor', component: DoctorRegistrationComponent },
  { path: 'passworddoctor', component: UpdatedoctorpasswordComponent },
  { path: 'doctorarea', component: DoctorAreaComponent },

  { path: 'patient', component: EspacePatientComponent },
  { path: 'registerpatient', component: PatientRegistrationComponent },
  { path: 'passwordpatient', component: UpdatepatientpasswordComponent },
  { path: 'familyarea', component: FamilyAreaComponent },

  { path: 'family', component: EspaceFamilleComponent },
  { path: 'regiterfamily', component: FamilyRegistrationComponent },
  { path: 'medicalassitantq', component: MedicalAssitantQComponent },

  { path: 'rdv/:id', component: AppointmentComponent },

  { path: 'updatepatient', component: UpdatePatientDataComponent },

  { path: 'personaldatafamily', component: PersonalDataFamilyComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
