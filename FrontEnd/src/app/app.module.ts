import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// dashboard components
import { LayoutComponent } from './dashboard/layout/layout.component';
import { TopBarComponent } from './dashboard/top-bar/top-bar.component';
import { OverlayComponent } from './dashboard/overlay/overlay.component';
import { SidebarComponent } from './dashboard/sidebar/sidebar/sidebar.component';
import { SidebarItemComponent } from './dashboard/sidebar/sidebar-item/sidebar-item.component';
import { SidebarItemsComponent } from './dashboard/sidebar/sidebar-items/sidebar-items.component';
import { SidebarHeaderComponent } from './dashboard/sidebar/sidebar-header/sidebar-header.component';

// pages
import { HomeComponent } from './pages/home/home.component';
import { CreditComponent } from './pages/credit/credit.component';
import { StatusComponent } from './pages/status/status.component';
import { ArchiveComponent } from './pages/archive/archive.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { DocIconComponent } from './dashboard/icons/doc-icon/doc-icon.component';
import { DocumentationComponent } from './pages/documentation/documentation.component';

// icons
import { HomeIconComponent } from './dashboard/icons/home-icon/home-icon.component';
import { CreditIconComponent } from './dashboard/icons/credit-icon/credit-icon.component';
import { StatusIconComponent } from './dashboard/icons/status-icon/status-icon.component';
import { ArchiveIconComponent } from './dashboard/icons/archive-icon/archive-icon.component';
import { SettingsIconComponent } from './dashboard/icons/settings-icon/settings-icon.component';

// others
import { DocComponent } from './components/docs/doc/doc.component';
import { ContentComponent } from './components/content/content.component';
import { SnippetComponent } from './components/docs/snippet/snippet.component';
import { FolderIconComponent } from './components/docs/icons/folder-icon/folder-icon.component';
import { EspaceDoctorComponent } from './Doctor/espace-doctor/espace-doctor.component';
import { FormsModule } from "@angular/forms";
import { DoctorService } from "../services/doctor.service";
import { FamilyService } from "../services/family.service";

//import {PatientService} from "../services/patient.service";
import {EspacePatientComponent} from './Patient/espace-patient/espace-patient.component';


import { HttpClientModule } from '@angular/common/http';
import { DoctorRegistrationComponent } from './Doctor/doctor-registration/doctor-registration.component';
import { UpdatedoctorpasswordComponent } from './Doctor/updatedoctorpassword/updatedoctorpassword.component';
import { DoctorAreaComponent } from './Doctor/doctor-area/doctor-area.component';
import { NgOptimizedImage } from "@angular/common";
<<<<<<< HEAD

=======
>>>>>>> 2254f2f9aaa23548609a463cd46d74596f3847d0

import { PatientAreaComponent } from './Patient/patient-area/patient-area.component';
import { PatientRegistrationComponent } from './Patient/patient-registration/patient-registration.component';
import { UpdatepatientpasswordComponent } from './Patient/updatepatientpassword/updatepatientpassword.component';
import { LoadingComponent } from './loading/loading.component';
import { EspaceFamilleComponent } from './Family/espace-famille/espace-famille.component';
import { FamilyRegistrationComponent } from './Family/family-registration/family-registration.component';
import { MedicalAssitantQComponent } from './Family/medical-assitant-q/medical-assitant-q.component';
import { FamilyAreaComponent } from './Family/family-area/family-area.component';
import { AppointmentComponent } from './appointment/appointment.component';

@NgModule({
  declarations: [
    AppComponent,

    // dashboard
    LayoutComponent,
    TopBarComponent,
    OverlayComponent,
    SidebarComponent,
    SidebarItemComponent,
    SidebarItemsComponent,
    SidebarHeaderComponent,

    // pages
    HomeComponent,
    CreditComponent,
    StatusComponent,
    ArchiveComponent,
    SettingsComponent,
    DocumentationComponent,

    // icons
    DocIconComponent,
    HomeIconComponent,
    ArchiveIconComponent,
    CreditIconComponent,
    StatusIconComponent,
    SettingsIconComponent,

    // others
    DocComponent,
    SnippetComponent,
    ContentComponent,
    FolderIconComponent,
    EspaceDoctorComponent,
    EspacePatientComponent,
    DoctorRegistrationComponent,
    UpdatedoctorpasswordComponent,
    DoctorAreaComponent,
    PatientAreaComponent,
    PatientRegistrationComponent,
    UpdatepatientpasswordComponent,

    LoadingComponent,

    EspaceFamilleComponent,
    FamilyRegistrationComponent,
    MedicalAssitantQComponent,
    FamilyAreaComponent,
    AppointmentComponent
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule, NgOptimizedImage],
  providers: [DoctorService,FamilyService],
  bootstrap: [AppComponent],
})
export class AppModule {}
