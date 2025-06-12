import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInsuranceFamilyComponent } from './update-insurance-family.component';

describe('UpdateInsuranceFamilyComponent', () => {
  let component: UpdateInsuranceFamilyComponent;
  let fixture: ComponentFixture<UpdateInsuranceFamilyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInsuranceFamilyComponent]
    });
    fixture = TestBed.createComponent(UpdateInsuranceFamilyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
