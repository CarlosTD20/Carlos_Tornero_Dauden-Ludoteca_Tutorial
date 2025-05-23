import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Client } from './model/Client';
import { CLIENT_DATA } from './model/mock-clients';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(
    private http: HttpClient
  ) { }

  private baseUrl = 'http://localhost:8080/client';

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.baseUrl);
  }

  saveClient(client: Client): Observable<Client> {
    const { id } = client;
    const url = id ? `${this.baseUrl}/${id}` : this.baseUrl;
    return this.http.put<Client>(url, client);
  }

  deleteClient(idClient: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idClient}`);
  }
}
