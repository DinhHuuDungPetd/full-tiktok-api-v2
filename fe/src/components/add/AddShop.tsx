"use client"
import Label from "@/components/form/Label";
import Input from "@/components/form/input/InputField";
import Button from "@/components/ui/button/Button";
import { useSearchParams } from 'next/navigation';
import { Suspense } from 'react';

function AddShopContent() {
  const searchParams = useSearchParams();
  const authCode = searchParams.get('code');

  return(
      <div className="w-full h-dvh flex justify-center items-center">
        <div className="flex flex-col gap-2 border-2 p-2">
          <h2 className="text-xl font-bold text-gray-900 text-center">Thêm shop</h2>
          <div className="min-w-[400px]">
            <Label>Tên shop (note)</Label>
            <Input type="text" placeholder="Nhập tên"/>
          </div>
          <div>
           <Button size={"sm"} variant={"add"} >Thêm</Button>
          </div>
          <div>{authCode}</div>
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