import { Component } from '@angular/core';
import {Session} from "./Models/session";
import {SessionService} from "./Sevices/session.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
}
