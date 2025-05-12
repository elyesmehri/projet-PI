import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspacePatientComponent } from './espace-patient.component';

describe('EspacePatientComponent', () => {
  let component: EspacePatientComponent;
  let fixture: ComponentFixture<EspacePatientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EspacePatientComponent]
    });
    fixture = TestBed.createComponent(EspacePatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
