import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { MedalTallyComponent } from './medal-tally.component';
import { Component } from '@angular/core';
import { MedalTallyModel } from '../../models/medalTally.model';

describe('MedalTallyComponent', () => {
  let component: MedalTallyComponent;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedalTallyComponent, ParentComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

@Component({
  selector: 'parent',
  template: '<app-medal-tally [medalTally]="medalTally"></app-medal-tally>'
})
class ParentComponent {
  medalTally: MedalTallyModel = { gold: 1, silver: 0, bronze: 3 };
}

