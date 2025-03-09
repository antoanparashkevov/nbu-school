"use client";

import React, {Fragment, useEffect, useState} from "react";
import {redirect} from "next/navigation";
import {getSession} from "@/lib/auth";

import BaseDialog from "@/components/UI/BaseDialog";

import type {Session} from '@/lib/auth';

const Home: React.FC = () => {
  const [session, setSession] = useState<null | Session>(null);
  const [selectedInterface, setSelectedInterface] = useState<string | null>(null);
  const [showDialog, setShowDialog] = useState<boolean>(true);
  const [isResolved, setIsResolved] = useState(false)

  useEffect(() => {
    getSession()
        .then((response) => {
          setSession(response)
          setIsResolved(true);

          if (response && response.roles.includes("USER") && !response.roles.includes("EMPLOYEE") && !response.roles.includes("ADMIN")) {
            setSelectedInterface("user");
            setShowDialog(false);
          }
        })
  }, []);

  useEffect(() => {
    if (!session && isResolved) {
      redirect("/login")
    }
  }, [session, isResolved]);

  const selectInterface = (selectedInterface: string) => {
    setSelectedInterface(selectedInterface)
    setShowDialog(false);
  }

  return (
      <Fragment>
          {session && showDialog &&
              <BaseDialog title="Choose an administrative role">
                <div className="flex gap-x-3 justify-center items-center">
                  <button className="base-btn-blue" onClick={() => selectInterface('user')}>User</button>
                  {!session.roles.includes("ADMIN") && <button className="base-btn-blue"
                                                               onClick={() => selectInterface('employee')}>Employee</button>}
                  {session.roles.includes("ADMIN") &&
                      <button className="base-btn-blue" onClick={() => selectInterface("admin")}>Admin</button>}
                </div>
              </BaseDialog>
          }
          {/*  TODO: Load the appropriate interface*/}
      </Fragment>
  );
}

export default Home;