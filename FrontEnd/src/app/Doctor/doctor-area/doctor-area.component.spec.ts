import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAreaComponent } from './doctor-area.component';

describe('DoctorAreaComponent', () => {
  let component: DoctorAreaComponent;
  let fixture: ComponentFixture<DoctorAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DoctorAreaComponent]
    });
    fixture = TestBed.createComponent(DoctorAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
