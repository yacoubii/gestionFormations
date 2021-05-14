import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { Formation } from '../Models/formation';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient,
    private tokenStorage: TokenStorageService) { }

  public getFormations(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/formation`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public linkToSession(sessionId:number, formationId:number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/formation/link/${sessionId}/${formationId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public getFormationEntity(): Observable<Formation[]> {
    return this.http.get<Formation[]>(`${this.apiServerUrl}/formation`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public addFormationEntity(Formation: Formation): Observable<Formation> {
    return this.http.post<Formation>(`${this.apiServerUrl}/formation`, Formation,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public updateFormationEntity(Formation: Formation,FormationId: number): Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/formation/${FormationId}`, Formation,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public deleteFormationEntity(FormationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/formation/${FormationId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }
}
