export interface Product {
    id: string;
    name: string;
    price: number;
    description: string;
    image: string;
}

export interface Item {
    id: string;
    quantity: number;
}

export interface Cart {
    id: string;
    items: Item[];
}