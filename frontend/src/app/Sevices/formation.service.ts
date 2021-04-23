import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient) { }

  public getFormations(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/formation`)
  }

  public linkToSession(sessionId:number, formationId:number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/formation/link/${sessionId}/${formationId}`)
  }
}
