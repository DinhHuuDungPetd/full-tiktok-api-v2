"use client"
import Label from "@/components/form/Label";
import Input from "@/components/form/input/InputField";
import Button from "@/components/ui/button/Button";
import { useSearchParams } from 'next/navigation';
import {Suspense, useState} from 'react';
import {toast} from "react-toastify";
import {ShopRequest} from "@/api/Type";
import {addShop} from "@/api/shopApi";

function AddShopContent() {
  const searchParams = useSearchParams();
  const authCode = searchParams.get('code') || '';

  const [name, setName] = useState('');
  const [note, setNote] = useState('');
  const [token, setToken] = useState('');

  const handleSubmit = async () => {
    const errors = [
      { condition: !name, message: "Name is required" },
      { condition: !authCode, message: "Authorization Code is required" },
      { condition: !token, message: "Token is required" },
    ];

    for (const error of errors) {
      if (error.condition) {
        toast.error(error.message);
        return;
      }
    }

    const body: ShopRequest = {
      token,
      note,
      authCode,
      shopName: name,
      categoryId: "String",
    };

    try {
      const response = await addShop(body);
      toast.success(response.message);
    } catch (e: unknown) {
      const error = e as Error;
      toast.error(error?.message || "Something went wrong");
    }
  };
  return(
      <div className="w-full h-dvh flex justify-center items-center">
        <div className="flex flex-col gap-2 border-2 p-4 rounded-lg">
          <h2 className="text-xl font-bold text-gray-900 text-center">Thêm shop</h2>
          <div className="min-w-[400px]">
            <Label>Tên shop *</Label>
            <Input
                type="text"
                placeholder="Nhập tên"
                defaultValue={name}
                onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className="min-w-[400px]">
            <Label>Ghi chú</Label>
            <Input
                type="text"
                placeholder="Thêm ghi chú"
                defaultValue={note}
                onChange={(e) => setNote(e.target.value)}
            />
          </div>
          <div className="min-w-[400px]">
            <Label>Token</Label>
            <Input
                type="text"
                placeholder="Paste token here"
                defaultValue={token}
                onChange={(e) => setToken(e.target.value)}
            />
          </div>
          <div>
           <Button size={"sm"} variant={"add"} onClick={handleSubmit} >Thêm</Button>
          </div>
        </div>
      </div>
  )
}

export default function FormAddShop() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <AddShopContent />
    </Suspense>
  );
}