'use client'
import {useParams} from "next/navigation";
import {useEffect} from "react";

export default function Order (){

  const params = useParams();
  const shopId = params.shopId as string;

  useEffect(() => {
    (async () =>{

    })();
  }, []);


  return <div>{shopId}</div>
}