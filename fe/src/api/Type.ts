type ApiResponse<T> = {
  status: number;
  message: string;
  result: T;
};

type LoginRequest = {
  email: string;
  password: string;
}
type UserResponse = {
  id: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  role: string;
  active: boolean;
  createdAt: Date;
}

export type ShopResponse = {
  shopId: string;
  shopName: string;
  note: string;
  country: string;
}

export type ShopRequest = {
  token: string;
  authCode: string;
  note: string;
  shopName: string;
  categoryId: string;
}

export interface Order {
  id: string;
  tracking_number: string;
  payment: {
    currency: string;
    total_amount: string;
  };
  recipient_address: {
    name: string;
    phone_number: string;
    address_detail: string;
    district_info: {
      address_level_name: string;
      address_name: string;
      address_level: string;
    }[];
    postal_code: string;
  };
  status: string;
  shipping_type: string;
  create_time: number;
  line_items: LineItem[];
}

export interface LineItem {
  product_name: string;
  sku_image: string;
  sku_id: string;
  sale_price: string;
  currency: string;
}

export interface OrderApiResponse {
  code: number;
  message: string;
  request_id: string;
  data: {
    next_page_token: string;
    total_count: number;
    orders: Order[];
  };
}

export type { ApiResponse , UserResponse , LoginRequest};