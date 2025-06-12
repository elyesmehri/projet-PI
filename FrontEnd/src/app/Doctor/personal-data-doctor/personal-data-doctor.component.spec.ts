import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDataDoctorComponent } from './personal-data-doctor.component';

describe('PersonalDataDoctorComponent', () => {
  let component: PersonalDataDoctorComponent;
  let fixture: ComponentFixture<PersonalDataDoctorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalDataDoctorComponent]
    });
    fixture = TestBed.createComponent(PersonalDataDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
