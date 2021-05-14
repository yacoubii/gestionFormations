import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import { Formateur } from '../Models/formateur';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FormateurService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient,
    private tokenStorage: TokenStorageService) { }

  public getFormateurs(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/formateur`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public linkToSession(sessionId:number, formateurId:number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/formateur/link/${sessionId}/${formateurId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public getFormateurEntity(): Observable<Formateur[]> {
    return this.http.get<Formateur[]>(`${this.apiServerUrl}/formateur`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public addFormateurEntity(Formateur: Formateur): Observable<Formateur> {
    return this.http.post<Formateur>(`${this.apiServerUrl}/formateur`, Formateur,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public updateFormateurEntity(Formateur: Formateur,FormateurId: number): Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/formateur/${FormateurId}`, Formateur,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public deleteFormateurEntity(FormateurId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/formateur/${FormateurId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }
}
