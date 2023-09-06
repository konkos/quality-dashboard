import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { GeneralStats } from "../../assets/models";

export const screen1Api = createApi({
  reducerPath: "screen1Api",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080/api/organizations",
    prepareHeaders: (headers, { getState }) => {
      const token = getState().auth.accessToken;
      console.log("Token: set", token);
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  endpoints: (builder) => ({
    getTopLanguages: builder.query({
      query: (organizationId: string) => ({
        url: `/${organizationId}/top-languages`,
      }),
    }),
    getLanguageNames: builder.query({
      query: (organizationId: string) => ({
        url: `/${organizationId}/language-names`,
      }),
    }),
    getGeneralStats: builder.query<GeneralStats, string>({
      query: (organizationId: string) => ({
        url: `/${organizationId}/general-stats`,
      }),
    }),
    getDateSinceLastAnalysis: builder.query({
      query: (organizationId: string) => ({
        url: `/${organizationId}/last-analysis-date`,
      }),
    }),
  }),
});

export const {
  useGetDateSinceLastAnalysisQuery,
  useGetGeneralStatsQuery,
  useGetLanguageNamesQuery,
  useGetTopLanguagesQuery,
} = screen1Api;