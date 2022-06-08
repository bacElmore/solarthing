import {Auth, useAuth} from "../authUtil";
import React, {useState} from "react";
import {useLoginQuery} from "../generated/graphql";
import {graphQLClient} from "../client";

export default function Login() {
  const [auth, setAuth] = useAuth();
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const { status, data, error, isLoading, isSuccess, refetch } = useLoginQuery(graphQLClient, { username, password }, { refetchOnWindowFocus: false, enabled: false});
  function submitLogin() {
    refetch().then(value => {
      if (value.isSuccess && !error) {
        const databaseAuthorization = value.data.databaseAuthorize;
        const auth: Auth = { databaseAuthorization };
        setAuth(auth);
      }
    });
  }
  return <>
    <div>
      { isLoading
        ? <>
          <p>Logging In...</p>
        </>
        : auth.databaseAuthorization ? <>
          <p>You are logged in</p>
          <button
            onClick={function () {
              setAuth({databaseAuthorization: undefined});
            }}
          >Log Out</button>
        </> : <>
          <p>Hello! Log in here!</p>
          <input value={username} onChange={function(event){
            setUsername(event.target.value)
          }}/>
          <br/>
          <input
            value={password}
            type="password"
            onChange={function(event){
            setPassword(event.target.value)
          }}/>
          <br/>
          <button
            onClick={function() {
              submitLogin();
            }}
          >Log in</button>
          { error && <p>Got error: {(error as any).message}</p>}

        </>}
    </div>
  </>;
}
