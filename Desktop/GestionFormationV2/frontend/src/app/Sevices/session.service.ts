import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Session} from '../Models/session'
import {environment} from "../../environments/environment";
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private apiServerUrl = environment.apiBaseUrl
  constructor(private http:HttpClient,
    private tokenStorage: TokenStorageService) {}

  public getSessions(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/session`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public getParticipantsOfSessions(sessionId:number): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/session/participants/${sessionId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public addSession(session:Session) :Observable<void>{
    return this.http.post<void>(`${this.apiServerUrl}/session`,session,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public updateSession(session:Session, sessionId:number|undefined):Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/session/${sessionId}`,session,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})
  }

  public deleteSession(sessionId:number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/session/${sessionId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}})

  }

  public clearFormationsForSession(sessionId:number):Observable<void>{
    return this.http.get<void>(`${this.apiServerUrl}/session/clear/${sessionId}`,{headers:{Authorization:`Bearer ${this.tokenStorage.getToken()}`}});
  }
}
