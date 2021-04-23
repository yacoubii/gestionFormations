import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Organisme} from "../Models/organisme";

@Injectable({
  providedIn: 'root'
})
export class OrganismeService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient) { }

  public getOrganismes(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/organisme`)
  }


  public linkToSession(sessionId:number, organismeId:number) : Observable<any> {
    return this.http.get<void>(`${this.apiServerUrl}/organisme/link2/${sessionId}/${organismeId}`,
      {headers:{Authorization:"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWRhbGkiLCJzY29wZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNjE4OTM5OTMxLCJleHAiOjE2MTg5NTc5MzF9.TNJ51rqaZipl5gsBv1d3Q6tZ0asbMeuRikYAmwm3C3s"}})
  }

  public linkToFormateur(formateurId:number, organismeId:number) : Observable<any> {
    return this.http.get<void>(`${this.apiServerUrl}/organisme/link1/${formateurId}/${organismeId}`,

      )
  }

}
