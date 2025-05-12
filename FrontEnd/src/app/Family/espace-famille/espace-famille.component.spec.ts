import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspaceFamilleComponent } from './espace-famille.component';

describe('EspaceFamilleComponent', () => {
  let component: EspaceFamilleComponent;
  let fixture: ComponentFixture<EspaceFamilleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EspaceFamilleComponent]
    });
    fixture = TestBed.createComponent(EspaceFamilleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
