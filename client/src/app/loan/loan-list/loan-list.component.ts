import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Loan } from '../model/Loan';
import { Game } from '../../game/model/Game';
import { Client } from '../../client/model/Client';
import { MAT_DATE_LOCALE, provideNativeDateAdapter } from '@angular/material/core';
import { Pageable } from '../../core/model/Pageable';
import { LoanService } from '../loan.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { ClientService } from '../../client/client.service';
import { GameService } from '../../game/game.service';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    MatPaginatorModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule
  ],
  providers: [
    provideNativeDateAdapter(),
    { provide: MAT_DATE_LOCALE, useValue: 'es-ES' },
  ],
  templateUrl: './loan-list.component.html',
  styleUrl: './loan-list.component.scss'
})
export class LoanListComponent implements OnInit {

  games: Game[];
  filterGame: Game;
  clients: Client[];
  filterClient: Client;
  filterDate: Date;

  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'nameGmae', 'nameClient', 'startDate', 'endDate', 'action'];

  constructor(
    private loanService: LoanService,
    private clientService: ClientService,
    protected gameService: GameService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadPage();

    this.clientService.getClients().subscribe((clients) => (this.clients = clients));
    this.gameService.getGames().subscribe((games) => (this.games = games));
  }

  loadPage(event?: PageEvent) {
    const pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [
        {
          property: 'id',
          direction: 'ASC'
        }
      ]
    }

    if (event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }

    const clientId = this.filterClient != null ? this.filterClient.id : null;
    const gameId = this.filterGame != null ? this.filterGame.id : null;

    this.loanService.getLoans(pageable, clientId, gameId, this.filterDate).subscribe((data) => {
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    });
  }

  onCleanFilter(): void {
    this.filterClient = null;
    this.filterGame = null;
    this.filterDate = null
    this.onSearch();
  }

  onSearch() {
    this.loadPage();
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Eliminar préstamo',
        description: 'Atención si borra el autor se perderán sus datos.<br> ¿Desea eliminar el préstamo?'
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe((result) => {
          this.ngOnInit();
        });
      }
    });
  }
}
