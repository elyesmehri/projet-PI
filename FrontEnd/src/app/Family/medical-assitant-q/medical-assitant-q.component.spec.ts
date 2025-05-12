import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalAssitantQComponent } from './medical-assitant-q.component';

describe('MedicalAssitantQComponent', () => {
  let component: MedicalAssitantQComponent;
  let fixture: ComponentFixture<MedicalAssitantQComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedicalAssitantQComponent]
    });
    fixture = TestBed.createComponent(MedicalAssitantQComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
