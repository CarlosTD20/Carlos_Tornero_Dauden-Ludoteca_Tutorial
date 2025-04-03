import { LoanPage } from "./LoanPage";

export const LOANS_DATA: LoanPage = {
    content: [
        { id: 1, game: { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, client: { id: 1, name: 'User 1' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
        { id: 2, game: { id: 2, title: 'Juego 2', age: 8, category: { id: 1, name: 'Categoría 1' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' } }, client: { id: 2, name: 'User 2' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
        { id: 3, game: { id: 3, title: 'Juego 3', age: 4, category: { id: 1, name: 'Categoría 1' }, author: { id: 3, name: 'Autor 3', nationality: 'Nacionalidad 3' } }, client: { id: 3, name: 'User 3' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
        { id: 4, game: { id: 4, title: 'Juego 4', age: 10, category: { id: 2, name: 'Categoría 2' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, client: { id: 4, name: 'User 4' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
        { id: 5, game: { id: 5, title: 'Juego 5', age: 16, category: { id: 2, name: 'Categoría 2' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' } }, client: { id: 1, name: 'User 5' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
        { id: 6, game: { id: 6, title: 'Juego 6', age: 16, category: { id: 2, name: 'Categoría 2' }, author: { id: 3, name: 'Autor 3', nationality: 'Nacionalidad 3' } }, client: { id: 2, name: 'User 6' }, fechaIni: new Date('2025-01-06'), fechaFin: new Date('2025-01-19') },
    ],
    pageable: {
        pageSize: 5,
        pageNumber: 0,
        sort: [{ property: ' id', direction: 'ASC' }]
    },
    totalElements: 6
}







