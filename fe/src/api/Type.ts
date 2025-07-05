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

export type { ApiResponse , UserResponse , LoginRequest};