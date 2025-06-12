import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInsuranceDoctorComponent } from './update-insurance-doctor.component';

describe('UpdateInsuranceDoctorComponent', () => {
  let component: UpdateInsuranceDoctorComponent;
  let fixture: ComponentFixture<UpdateInsuranceDoctorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInsuranceDoctorComponent]
    });
    fixture = TestBed.createComponent(UpdateInsuranceDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
