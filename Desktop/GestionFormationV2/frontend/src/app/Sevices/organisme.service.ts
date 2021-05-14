import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Organisme} from "../Models/organisme";
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class OrganismeService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient,
    private tokenStorage: TokenStorageService) { }

  public getOrganismes(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/organisme`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }


  public linkToSession(sessionId:number, organismeId:number) : Observable<any> {
    return this.http.get<void>(`${this.apiServerUrl}/organisme/link2/${sessionId}/${organismeId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public linkToFormateur(formateurId:number, organismeId:number) : Observable<any> {
    return this.http.get<void>(`${this.apiServerUrl}/organisme/link1/${formateurId}/${organismeId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public getOrganisme(): Observable<Organisme[]> {
    return this.http.get<Organisme[]>(`${this.apiServerUrl}/organisme`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public addOrganisme(Organisme: Organisme): Observable<Organisme> {
    return this.http.post<Organisme>(`${this.apiServerUrl}/organisme`, Organisme,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public updateOrganisme(Organisme: Organisme,OrganismeId: number): Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/organisme/${OrganismeId}`, Organisme,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public deleteOrganisme(OrganismeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/organisme/${OrganismeId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }

  public linkOrganismeToFormateur(formateurId:number, organismeId:number) : Observable<any> {
    return this.http.get<void>(`${this.apiServerUrl}/organisme/link1/${formateurId}/${organismeId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }
}
