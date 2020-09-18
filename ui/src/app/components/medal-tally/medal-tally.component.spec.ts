import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { MedalTallyComponent } from './medal-tally.component';
import { Component } from '@angular/core';
import { MedalTallyModel } from '../../models/medalTally.model';

fdescribe('MedalTallyComponent', () => {
  let component: MedalTallyComponent;
  let fixture: ComponentFixture<ParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedalTallyComponent, ParentComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ParentComponent);
    component = fixture.debugElement.children[0].componentInstance;
    // fixture.detectChanges();
  }));

  it('should create', () => {
    console.log(component.medalTally);
    expect(component).toBeTruthy();
  });

  it('should set medalTallyKeys according to medalTally object', () => {
    console.log(component.medalTally);
    // component.medalTally = { gold: 1, silver: 0, bronze: 3 };
    // component.ngOnInit();
    expect(component.medalTallyKeys[0]).toEqual('gold');
  });
});

@Component({
  selector: 'parent',
  template: '<app-medal-tally [medalTally]="medalTally"></app-medal-tally>'
})
class ParentComponent {
  medalTally: MedalTallyModel = { gold: 1, silver: 0, bronze: 3 };
}

