import { Component, OnInit, ViewEncapsulation , AfterViewInit} from '@angular/core';
import * as tt from '@tomtom-international/web-sdk-maps';
import {environment} from "../environments/environment";
import { Observable, Subscriber } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'Frontend';



}
