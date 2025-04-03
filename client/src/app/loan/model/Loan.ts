import { Client } from "../../client/model/Client";
import { Game } from "../../game/model/Game";

export class Loan {
    id: number;
    client: Client;
    game: Game;
    fechaIni: Date;
    fechaFin: Date;
}