import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspaceDoctorComponent } from './espace-doctor.component';

describe('EspaceDoctorComponent', () => {
  let component: EspaceDoctorComponent;
  let fixture: ComponentFixture<EspaceDoctorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EspaceDoctorComponent]
    });
    fixture = TestBed.createComponent(EspaceDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
