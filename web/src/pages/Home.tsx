import {useHomeQuery} from "../generated/graphql";
import {graphQLClient} from "../client";
import React from "react";
import {getTimeMillisRounded} from "../timeUtil";
import Layout from "../layout/Layout";
import {useSourceId} from "../sourceUtil";
import styles from './SolarThing.module.css';

function BatteryVoltage() {
  const [sourceId] = useSourceId();
  // We have to use a rounded up value to make sure that useQuery doesn't keep reloading the data
  const timeMillisRounded = getTimeMillisRounded();
  const {data, error, isLoading, isSuccess} = useHomeQuery(graphQLClient, { sourceId, currentTimeMillis: "" + timeMillisRounded});
  return <>
    {!isSuccess
      ? <p>Loading Data</p>
      : <p>Battery: {isSuccess && data!.queryStatusLast.batteryVoltageAverage[0].data} V</p>}
  </>
}

export default function Home() {
  return <>
    <Layout>
      <div className={styles.contentDiv}>
        <header className="App-header">
          <h1>SolarThing</h1>
          <BatteryVoltage/>

        </header>
      </div>
    </Layout>
  </>
}
