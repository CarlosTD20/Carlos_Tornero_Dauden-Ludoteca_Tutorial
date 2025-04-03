import { Injectable } from '@angular/core';
import { Pageable } from '../core/model/Pageable';
import { Observable, of } from 'rxjs';
import { LoanPage } from './model/LoanPage';
import { LOANS_DATA } from './model/mock-loans';
import { Loan } from './model/Loan';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(
    private http: HttpClient
  ) { }

  private baseUrl = 'http://localhost:8080/loan';

  getLoans(pageable: Pageable, clientId?: number, gameId?: number, date?: Date): Observable<LoanPage> {
    return this.http.post<LoanPage>(this.composeFindUrl(clientId, gameId, date), {pageable: pageable});
  }

  saveLoan(loan: Loan): Observable<Loan> {
    console.log(JSON.stringify(loan))

    const loanSave = {
      ...loan,
      fechaIni : this.fomatDate(loan.fechaIni),
      fechaFin: this.fomatDate(loan.fechaFin)
    }

    console.log(JSON.stringify(loanSave))
  
    return this.http.put<Loan>(this.baseUrl, loanSave);
  }

  deleteLoan(loanId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${loanId}`);
  }

  private composeFindUrl(clientId?: number, gameId?: number, date?: Date): string {
    const params = new URLSearchParams();
    if (clientId) {
      params.set('clientId', clientId.toString());
    }
    if (gameId) {
      params.set('gameId', gameId.toString())
    }
    if (date) {
      params.set('date', this.fomatDate(date))
    }
    const queryString = params.toString();
    return queryString ? `${this.baseUrl}?${queryString}` : this.baseUrl;
  }

  fomatDate(date: Date) : string { 
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); 
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  }
}
