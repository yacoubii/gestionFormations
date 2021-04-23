import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FormateurService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient) { }

  public getFormateurs(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/formateur`)
  }

  public linkToSession(sessionId:number, formateurId:number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/formateur/link/${sessionId}/${formateurId}`)
  }
}
